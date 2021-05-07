import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import * as clone from 'lodash/clone';
import { Person } from 'src/app/model/person';
import { FormControl, Validators } from '@angular/forms';
import * as cloneDeep from 'lodash/cloneDeep';
import * as isEmpty from 'node_modules/lodash/isEmpty';

@Component({
  selector: 'app-create-person',
  templateUrl: './create-person.component.html',
  styleUrls: ['./create-person.component.css']
})
export class CreatePersonComponent implements OnInit {

  @Output() personValidation =new EventEmitter<boolean>();
  @Output() emitPersons = new EventEmitter<Person[]>();
  @Input() personsArray :Person[];

  isPersonVisible: boolean;
  addingPerson: Person;
  personArrayLeng: number;
  enableAddingForm: boolean;

  formEmail: Map<number, FormControl> = new Map<number, FormControl>();
  formLastName: Map<number, FormControl> = new Map<number, FormControl>();
  formFirstName: Map<number, FormControl> = new Map<number, FormControl>();
  personId:any;


  persons:Person[];

  constructor() { }

 async ngOnInit(): Promise<void> {

   if( !isEmpty(this.personsArray))
   {
              this.persons=[];
              this.persons=[...this.personsArray];
   }

    this.personArrayLeng = 0;
    this.enableAddingForm=true;

    await  this.initializePerson();

  }


  inputFormValidatorsPerson(id: any) {
    this.formEmail.set( id, new FormControl(' ', [Validators.required, Validators.minLength(4)] ,  ) );
    this.formFirstName.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4)]) );
    this.formLastName.set( id,new FormControl(' ', [Validators.required, Validators.minLength(4)]) );
  }


 async addPerson() {

    if( !this.getPersonValidation() ) {
      this.personValidation.emit(false);
      return;
    }

     const person: Person = clone(this.addingPerson);

     if ( !isEmpty(this.persons) ) {
       person.id =  this.persons[this.persons.length - 1].id +1;
       this.persons = [...this.persons, person];
     }
     else
     {
       this.persons=[];
       this.persons = [...this.persons, person];
     }

     this.personArrayLeng = this.persons.length;

     this.addingPerson = {
       id: 0,
       firstName: '',
       lastName: '',
       email: '',
     };

     this.enableAddingForm = false;

     const personsCp:Person[] = cloneDeep(this.persons);

     await this.personValidation.emit(true);
     await this.emitPersons.emit(personsCp);
    }


    isVisibleAddingPerson(){
      this.enableAddingForm=true;
    }


 async  removePerson(idPerson: number) {
    console.log('removing person with  id: ' + idPerson);

    let personsRemove: any = [];

    for (let person of this.persons) {
      if (person.id !== idPerson)
        personsRemove = [...personsRemove, person];
    }

    this.persons = [...personsRemove];

    this.personArrayLeng = this.persons.length;

    if(this.personArrayLeng < 1) {
      this.isVisibleAddingPerson();
      await  this.personValidation.emit(false);
    }

    const personsCp:Person[] = cloneDeep(this.persons);
    await this.emitPersons.emit(personsCp);
  }

  cancelAddPerson(){
    this.addingPerson = {
      id: 0,
      firstName: '',
      lastName: '',
      email: '',
    };
    this.enableAddingForm = false;
  }

 async initializePerson() {

    this.addingPerson = {
      id: 0,
      firstName: '',
      lastName: '',
      email: '',
    };

    this.isPersonVisible=true;

    this.inputFormValidatorsPerson(this.addingPerson.id);

    if (  !isEmpty(this.persons) ){
    this.personArrayLeng=this.persons.length;
    this.enableAddingForm=false;
    }
  }


 getPersonValidation(): boolean {
   if (this.enableAddingForm) {
     if (
        this.formEmail.get(this.addingPerson.id).invalid     ||
        this.formFirstName.get(this.addingPerson.id).invalid ||
        this.formLastName.get(this.addingPerson.id).invalid
      )
        return false;
    }
   return true;
}

}
