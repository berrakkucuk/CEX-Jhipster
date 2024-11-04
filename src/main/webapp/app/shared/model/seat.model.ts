import { type IBooking } from '@/shared/model/booking.model';
import { type IScreen } from '@/shared/model/screen.model';

export interface ISeat {
  id?: number;
  seatNumber?: string | null;
  seatType?: string | null;
  booking?: IBooking | null;
  screen?: IScreen | null;
}

export class Seat implements ISeat {
  constructor(
    public id?: number,
    public seatNumber?: string | null,
    public seatType?: string | null,
    public booking?: IBooking | null,
    public screen?: IScreen | null,
  ) {}
}
