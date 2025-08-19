export interface IProduct {
  id: number;
  name: string;
  description: string;
  price: number;
  imageUrl?: string;
}

export interface IProductUpdate{
  id?: number;
  name?: string,
  description?: string,
  category?: string,
  price?: number
}
