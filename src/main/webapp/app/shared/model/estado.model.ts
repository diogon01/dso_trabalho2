import { IPais } from 'app/shared/model/pais.model';

export interface IEstado {
  id?: number;
  nomeEstado?: string;
  pais?: IPais;
}

export class Estado implements IEstado {
  constructor(public id?: number, public nomeEstado?: string, public pais?: IPais) {}
}
