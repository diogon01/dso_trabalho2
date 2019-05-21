import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAnuncioHistorico } from 'app/shared/model/anuncio-historico.model';

type EntityResponseType = HttpResponse<IAnuncioHistorico>;
type EntityArrayResponseType = HttpResponse<IAnuncioHistorico[]>;

@Injectable({ providedIn: 'root' })
export class AnuncioHistoricoService {
  public resourceUrl = SERVER_API_URL + 'api/anuncio-historicos';

  constructor(protected http: HttpClient) {}

  create(anuncioHistorico: IAnuncioHistorico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anuncioHistorico);
    return this.http
      .post<IAnuncioHistorico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(anuncioHistorico: IAnuncioHistorico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anuncioHistorico);
    return this.http
      .put<IAnuncioHistorico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAnuncioHistorico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAnuncioHistorico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(anuncioHistorico: IAnuncioHistorico): IAnuncioHistorico {
    const copy: IAnuncioHistorico = Object.assign({}, anuncioHistorico, {
      dataInicio:
        anuncioHistorico.dataInicio != null && anuncioHistorico.dataInicio.isValid() ? anuncioHistorico.dataInicio.toJSON() : null,
      dataFinal: anuncioHistorico.dataFinal != null && anuncioHistorico.dataFinal.isValid() ? anuncioHistorico.dataFinal.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataInicio = res.body.dataInicio != null ? moment(res.body.dataInicio) : null;
      res.body.dataFinal = res.body.dataFinal != null ? moment(res.body.dataFinal) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((anuncioHistorico: IAnuncioHistorico) => {
        anuncioHistorico.dataInicio = anuncioHistorico.dataInicio != null ? moment(anuncioHistorico.dataInicio) : null;
        anuncioHistorico.dataFinal = anuncioHistorico.dataFinal != null ? moment(anuncioHistorico.dataFinal) : null;
      });
    }
    return res;
  }
}
