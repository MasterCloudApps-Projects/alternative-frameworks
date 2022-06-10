export class Item {
    id: number;
    description: string;
    checked: boolean;

    constructor(description: string, checked:boolean) {
        this.description = description;
        this.checked = checked;
    }
}