export class Post {
    constructor(
        public id: string,
        public title: string,
        public description: string,
        public content: boolean,
        public createdAt: Date,
        public updatedAt: Date,
    ){}
}