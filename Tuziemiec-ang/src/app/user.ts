import { Variable } from "@angular/compiler/src/render3/r3_ast";

export interface User {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    dayOfBirth: Date;
    age: number

}