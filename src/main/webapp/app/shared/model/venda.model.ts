import { IAnuncio } from 'app/shared/model/anuncio.model';

export interface IVenda {
  id?: number;
  titulo?: string;
  resumo?: string;
  job?: IAnuncio;
}

export class Venda implements IVenda {
  constructor(public id?: number, public titulo?: string, public resumo?: string, public job?: IAnuncio) {}
}
