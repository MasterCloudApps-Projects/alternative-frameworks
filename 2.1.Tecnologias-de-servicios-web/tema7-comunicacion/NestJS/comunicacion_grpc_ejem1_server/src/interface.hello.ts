export interface HelloRequest {
    firstName: string;
    lastName: string;
}

export class HelloResponse {
    greeting: string;
    
    constructor(gretting: string) {
        this.greeting = gretting;
    }
}