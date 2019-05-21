import { IEstado } from 'app/shared/model/estado.model';

export interface IEndereco {
  id?: number;
  rua?: string;
  zip?: string;
  cidade?: string;
  urf?: string;
  estado?: IEstado;
}

export class Endereco implements IEndereco {
  constructor(
    public id?: number,
    public rua?: string,
    public zip?: string,
    public cidade?: string,
    public urf?: string,
    public estado?: IEstado
  ) {}
}
