import { Participation } from './participation'

export interface Review {
    id: number;
    commentHeader: string;
    commentBody: string;
    commentDate: Date;
    rating: number;
    upVote: number;
    downVote: number;
    templateId: number;
    participation: Participation;
}