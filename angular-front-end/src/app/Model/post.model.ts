export class Post {
    constructor(
        public id: number,
        public title: string,
        public description: string,
        public content: boolean,
        public createdAt: Date,
        public updatedAt: Date,
    ){}
}