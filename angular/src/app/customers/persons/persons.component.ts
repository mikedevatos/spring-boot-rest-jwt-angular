import { Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import { Person } from '../../model/person';
import { HttpRequestsService } from '../../services/http-requests.service';
import { MessageResultService } from '../../services/message-result.service';
import { Validators, FormControl } from '@angular/forms';
import * as clone from 'lodash/clone';
import { PersonDTO } from '../../model/person-dto';

@Component({
  selector: 'app-persons',
  templateUrl: './persons.component.html',
  styleUrls: ['./persons.component.css']
})
export class PersonsComponent implements OnInit {

  @Input() persons:any[];
  @Input() customerId:number;
  @Output() reloadData =new EventEmitter<any>();

  isVisibleEditingPerson: boolean;
  personEditInput: Person;

  formEmail: Map<number, FormControl> = new Map<number, FormControl>();
  formLastName: Map<number, FormControl> = new Map<number, FormControl>();
  formFirstName: Map<number, FormControl> = new Map<number, FormControl>();

  newPersonId:any;
  isVisiblePersonAdd:boolean;


  constructor( private httpService: HttpRequestsService,
               private messageResult: MessageResultService ) { }

  ngOnInit(): void { }

  destroyModalAddPerson() {
    this.isVisiblePersonAdd=false;
  }

  destroyModalEditingPerson() {
    this.isVisibleEditingPerson = false;
  }

  inputFormValidatorsPerson(id: any) {
    this.formEmail.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4)]));
    this.formFirstName.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4)]));
    this.formLastName.set(id, new FormControl(' ', [Validators.required, Validators.minLength(4)]));
  }


  startEditingPerson(idPerson: any): void {

    this.personEditInput = {};

    const person: Person = this.persons.find(item => item.id === idPerson);

    this.inputFormValidatorsPerson(idPerson);

    this.personEditInput = clone(person);

    this.isVisibleEditingPerson=true;
  }


  startAddingPerson(){

  this.newPersonId=0;

  this.personEditInput = {};

  this.inputFormValidatorsPerson(this.newPersonId);

  this.isVisiblePersonAdd=true;
}


create(){
  const person:PersonDTO={
  firstName: this.personEditInput.firstName,
  lastName: this.personEditInput.lastName,
  email: this.personEditInput.email,
  customerId: this.customerId
 };

 this.postPerson(person);
 }

  update() {
    const person = clone(this.personEditInput);
    this.putPerson(person)
  }


  postPerson(personDto:PersonDTO){
    this.httpService.postRequestPerson(personDto)
    .subscribe(res => { console.log(res); },
      error => {
        console.log(error);
        this.messageResult.error('something went wrong saving person');
      },
      () => {
        this.destroyModalAddPerson();
        this.messageResult.success('succes saving person');
        this.reloadData.emit();
      });
  }


  putPerson(person: Person) {
    this.httpService.putRequestPerson(person)
      .subscribe(
        res => console.log(res),
        error => {
          this.messageResult.error('error updating person record something went wrong');
          console.log(error);
          this.reloadData.emit();
        },
        () => {
          this.reloadData.emit();
          this.messageResult.success('success updating person');
        }
      )
  }

     delete(idPerson:number){
           this.httpService.deleteRequestPerson(idPerson)
           .subscribe(res => console.log(res),
           error => {
             console.log(error);
             this.messageResult.error('something went wrong deleting person');
           },
           () => {
             this.messageResult.success('success deleting person');
             this.reloadData.emit();
           });
       }
}
