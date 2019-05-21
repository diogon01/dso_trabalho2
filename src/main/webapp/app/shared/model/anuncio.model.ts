import { IVenda } from 'app/shared/model/venda.model';
import { IEndereco } from 'app/shared/model/endereco.model';

export const enum Categoria {
  LIVRO = 'LIVRO',
  INFORMATICA = 'INFORMATICA',
  ESPORTIVOS = 'ESPORTIVOS',
  LAZER = 'LAZER',
  OUTROS = 'OUTROS'
}

export interface IAnuncio {
  id?: number;
  titulo?: string;
  valorMin?: number;
  valorMax?: number;
  categoria?: Categoria;
  tituloVenda?: IVenda;
  endereco?: IEndereco;
}

export class Anuncio implements IAnuncio {
  constructor(
    public id?: number,
    public titulo?: string,
    public valorMin?: number,
    public valorMax?: number,
    public categoria?: Categoria,
    public tituloVenda?: IVenda,
    public endereco?: IEndereco
  ) {}
}
