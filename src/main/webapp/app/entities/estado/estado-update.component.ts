import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEstado, Estado } from 'app/shared/model/estado.model';
import { EstadoService } from './estado.service';
import { IPais } from 'app/shared/model/pais.model';
import { PaisService } from 'app/entities/pais';

@Component({
  selector: 'jhi-estado-update',
  templateUrl: './estado-update.component.html'
})
export class EstadoUpdateComponent implements OnInit {
  estado: IEstado;
  isSaving: boolean;

  pais: IPais[];

  editForm = this.fb.group({
    id: [],
    nomeEstado: [],
    pais: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected estadoService: EstadoService,
    protected paisService: PaisService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ estado }) => {
      this.updateForm(estado);
      this.estado = estado;
    });
    this.paisService
      .query({ filter: 'estado-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPais[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPais[]>) => response.body)
      )
      .subscribe(
        (res: IPais[]) => {
          if (!this.estado.pais || !this.estado.pais.id) {
            this.pais = res;
          } else {
            this.paisService
              .find(this.estado.pais.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPais>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPais>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPais) => (this.pais = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(estado: IEstado) {
    this.editForm.patchValue({
      id: estado.id,
      nomeEstado: estado.nomeEstado,
      pais: estado.pais
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const estado = this.createFromForm();
    if (estado.id !== undefined) {
      this.subscribeToSaveResponse(this.estadoService.update(estado));
    } else {
      this.subscribeToSaveResponse(this.estadoService.create(estado));
    }
  }

  private createFromForm(): IEstado {
    const entity = {
      ...new Estado(),
      id: this.editForm.get(['id']).value,
      nomeEstado: this.editForm.get(['nomeEstado']).value,
      pais: this.editForm.get(['pais']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstado>>) {
    result.subscribe((res: HttpResponse<IEstado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPaisById(index: number, item: IPais) {
    return item.id;
  }
}
