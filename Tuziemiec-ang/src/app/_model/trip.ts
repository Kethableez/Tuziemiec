import { TripTemplate } from "./TripTemplate";

export interface Trip {
    id: number;
    template: TripTemplate;
    startDate: Date;
    endDate: Date;
    userLimit: number;
    booking: number;
}