export interface HelloResponse {
    firstName: string;
    lastName: string;
}

export interface HelloRequest {
    firstName: string;
    lastName: string;
    
}

export interface HelloService {
    hello(helloRequest: HelloRequest): HelloResponse;
}