import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators  } from '@angular/forms';
import { UserService } from '../_services/user.service';

@Component({ 
    templateUrl: 'register.component.html',
    styleUrls: ['register.component.css']
})
export class RegisterComponent{
    get username() {
        return this.registerForm.get('username');
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

    isSubmit = false;

    constructor(private fb: FormBuilder, private userService: UserService) {}

    registerForm = this.fb.group({
        username: ['', [Validators.required, Validators.minLength(6)]],
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        email: ['', Validators.required],
        password: ['', Validators.required],
        dayOfBirth: ['', Validators.required]
    });

    onSubmit(){
        console.log(this.registerForm.value);
        this.userService.register(this.registerForm.value).subscribe(
            response => console.log('Success!', response),
            error => console.error('Error!', error)
        );

        this.isSubmit = true;
    }
}