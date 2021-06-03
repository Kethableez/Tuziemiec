import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl, ReactiveFormsModule, AbstractControl  } from '@angular/forms';
import { UserService } from '../_services/user.service';
import { MustMatch } from './password.validator';
import { format, formatDistance, formatRelative, isPast, subDays } from 'date-fns';
import { Router } from '@angular/router';

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

    get password () {
        return this.registerForm.get('password');
    }

    get confirmPassword(){
        return this.registerForm.get('confirmPassword');
    }

    get dayOfBirth() {
        return this.registerForm.get('dayOfBirth');
    }

    get f() { 
        return this.registerForm.controls; 
    }


    isSubmit = false;
    failedRegister = false;
    errorMessage: string;
    isDatePast = false;
    isTried = false;

    constructor(private fb: FormBuilder, 
        private userService: UserService,
        private router: Router) {}


    registerForm = this.fb.group({
        username: ['', [Validators.required, Validators.minLength(6)]],
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        email: new FormControl('', Validators.compose([
            Validators.required,
            Validators.pattern('^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$')
        ])),
        password: ['', Validators.required],
        confirmPassword: ['', Validators.required],
        dayOfBirth: ['', Validators.required]
    }, {
        validator: MustMatch('password', 'confirmPassword')
    });

    onSubmit(){
        console.log(this.registerForm.value);
        this.chceckDate();
        if (this.isDatePast){
            this.userService.register(this.registerForm.value).subscribe(
                response => {
                    this.router.navigate(['/login']);
                },
                err => {
                    this.errorMessage = err.error.message;
                    this.failedRegister = true;
                }
            );
    
            this.isSubmit = true;
        }

    }

    reloadPage(): void {
        window.location.reload();
      }

    chceckDate() : void{
        this.isDatePast = isPast(new Date(this.dayOfBirth.value));
        this.isTried = true;
    }
}