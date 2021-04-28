import { Attraction } from "./attraction";

export interface TripTemplate {
    id: number;
    name: string;
    place: string;
    description: string;
    guideId: number;
    rating: number;
    attractions: Attraction[];
}