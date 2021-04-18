import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators  } from '@angular/forms';
import { RegisterService } from './register.service';

@Component({ 
    templateUrl: 'register.component.html',
    styleUrls: ['register.component.css']
})
export class RegisterComponent{
    get userName() {
        return this.registerForm.get('userName');
    }

    get firstName(){
        return this.registerForm.get('firstName');
    }

    get lastName() {
        return this.registerForm.get('lastName');
    }

    get email() {
        return this.registerForm.get('email');
    }

    get password() {
        return this.registerForm.get('password');
    }

    get dayOfBirth() {
        return this.registerForm.get('dayOfBirth');
    }

    constructor(private fb: FormBuilder, private registerService: RegisterService) {}

    registerForm = this.fb.group({
        userName: ['', [Validators.required, Validators.minLength(6)]],
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        email: ['', Validators.required],
        password: ['', Validators.required],
        dayOfBirth: ['', Validators.required]
    });

    onSubmit(){
        console.log(this.registerForm.value);
        this.registerService.register(this.registerForm.value).subscribe(
            response => console.log('Success!', response),
            error => console.error('Error!', error)
        );
    }
}