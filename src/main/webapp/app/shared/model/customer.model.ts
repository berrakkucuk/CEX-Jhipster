export interface ICustomer {
  id?: number;
  name?: string | null;
  email?: string | null;
  phoneNumber?: string | null;
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public name?: string | null,
    public email?: string | null,
    public phoneNumber?: string | null,
  ) {}
}
