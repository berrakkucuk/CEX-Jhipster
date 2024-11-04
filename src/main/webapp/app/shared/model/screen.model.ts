import { type IMovie } from '@/shared/model/movie.model';
import { type IBooking } from '@/shared/model/booking.model';

export interface IScreen {
  id?: number;
  name?: string | null;
  totalSeats?: number | null;
  movie?: IMovie | null;
  booking?: IBooking | null;
}

export class Screen implements IScreen {
  constructor(
    public id?: number,
    public name?: string | null,
    public totalSeats?: number | null,
    public movie?: IMovie | null,
    public booking?: IBooking | null,
  ) {}
}
