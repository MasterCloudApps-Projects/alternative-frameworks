export class PostDTO {
    id: number;
    user: string;
    title: string;
    text: string;

    constructor(user: string, title:string, text:string) {
        this.user = user;
        this.title = title;
        this.text = text;
    }
}