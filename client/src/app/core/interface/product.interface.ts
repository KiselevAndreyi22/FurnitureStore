export interface IProduct {
  id: number;
  name: string;
  description: string;
  price: number;
  imageUrl?: string;
}

export interface IProductUpdate{
  name?: string,
  description?: string,
  price?: number
}
