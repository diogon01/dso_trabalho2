import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAnuncioHistorico, AnuncioHistorico } from 'app/shared/model/anuncio-historico.model';
import { AnuncioHistoricoService } from './anuncio-historico.service';
import { IAnuncio } from 'app/shared/model/anuncio.model';
import { AnuncioService } from 'app/entities/anuncio';

@Component({
  selector: 'jhi-anuncio-historico-update',
  templateUrl: './anuncio-historico-update.component.html'
})
export class AnuncioHistoricoUpdateComponent implements OnInit {
  anuncioHistorico: IAnuncioHistorico;
  isSaving: boolean;

  anuncios: IAnuncio[];

  editForm = this.fb.group({
    id: [],
    dataInicio: [],
    dataFinal: [],
    job: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected anuncioHistoricoService: AnuncioHistoricoService,
    protected anuncioService: AnuncioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ anuncioHistorico }) => {
      this.updateForm(anuncioHistorico);
      this.anuncioHistorico = anuncioHistorico;
    });
    this.anuncioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnuncio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnuncio[]>) => response.body)
      )
      .subscribe((res: IAnuncio[]) => (this.anuncios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(anuncioHistorico: IAnuncioHistorico) {
    this.editForm.patchValue({
      id: anuncioHistorico.id,
      dataInicio: anuncioHistorico.dataInicio != null ? anuncioHistorico.dataInicio.format(DATE_TIME_FORMAT) : null,
      dataFinal: anuncioHistorico.dataFinal != null ? anuncioHistorico.dataFinal.format(DATE_TIME_FORMAT) : null,
      job: anuncioHistorico.job
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const anuncioHistorico = this.createFromForm();
    if (anuncioHistorico.id !== undefined) {
      this.subscribeToSaveResponse(this.anuncioHistoricoService.update(anuncioHistorico));
    } else {
      this.subscribeToSaveResponse(this.anuncioHistoricoService.create(anuncioHistorico));
    }
  }

  private createFromForm(): IAnuncioHistorico {
    const entity = {
      ...new AnuncioHistorico(),
      id: this.editForm.get(['id']).value,
      dataInicio:
        this.editForm.get(['dataInicio']).value != null ? moment(this.editForm.get(['dataInicio']).value, DATE_TIME_FORMAT) : undefined,
      dataFinal:
        this.editForm.get(['dataFinal']).value != null ? moment(this.editForm.get(['dataFinal']).value, DATE_TIME_FORMAT) : undefined,
      job: this.editForm.get(['job']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnuncioHistorico>>) {
    result.subscribe((res: HttpResponse<IAnuncioHistorico>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAnuncioById(index: number, item: IAnuncio) {
    return item.id;
  }
}
