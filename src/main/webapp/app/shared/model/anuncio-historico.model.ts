import { Moment } from 'moment';
import { IAnuncio } from 'app/shared/model/anuncio.model';

export interface IAnuncioHistorico {
  id?: number;
  dataInicio?: Moment;
  dataFinal?: Moment;
  job?: IAnuncio;
}

export class AnuncioHistorico implements IAnuncioHistorico {
  constructor(public id?: number, public dataInicio?: Moment, public dataFinal?: Moment, public job?: IAnuncio) {}
}
