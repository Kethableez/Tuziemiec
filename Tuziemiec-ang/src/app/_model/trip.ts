export interface Trip {
    id: number;
    city: string;
    name: string;
    description: string;
    userLimit: number;
    tripDate: Date;
    guideId: number;
    isAvaliable: boolean;
}