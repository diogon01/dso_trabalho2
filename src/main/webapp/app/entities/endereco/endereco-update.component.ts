import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEndereco, Endereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from './endereco.service';
import { IEstado } from 'app/shared/model/estado.model';
import { EstadoService } from 'app/entities/estado';

@Component({
  selector: 'jhi-endereco-update',
  templateUrl: './endereco-update.component.html'
})
export class EnderecoUpdateComponent implements OnInit {
  endereco: IEndereco;
  isSaving: boolean;

  estados: IEstado[];

  editForm = this.fb.group({
    id: [],
    rua: [],
    zip: [],
    cidade: [],
    urf: [],
    estado: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected enderecoService: EnderecoService,
    protected estadoService: EstadoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ endereco }) => {
      this.updateForm(endereco);
      this.endereco = endereco;
    });
    this.estadoService
      .query({ filter: 'endereco-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IEstado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEstado[]>) => response.body)
      )
      .subscribe(
        (res: IEstado[]) => {
          if (!this.endereco.estado || !this.endereco.estado.id) {
            this.estados = res;
          } else {
            this.estadoService
              .find(this.endereco.estado.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IEstado>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IEstado>) => subResponse.body)
              )
              .subscribe(
                (subRes: IEstado) => (this.estados = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(endereco: IEndereco) {
    this.editForm.patchValue({
      id: endereco.id,
      rua: endereco.rua,
      zip: endereco.zip,
      cidade: endereco.cidade,
      urf: endereco.urf,
      estado: endereco.estado
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const endereco = this.createFromForm();
    if (endereco.id !== undefined) {
      this.subscribeToSaveResponse(this.enderecoService.update(endereco));
    } else {
      this.subscribeToSaveResponse(this.enderecoService.create(endereco));
    }
  }

  private createFromForm(): IEndereco {
    const entity = {
      ...new Endereco(),
      id: this.editForm.get(['id']).value,
      rua: this.editForm.get(['rua']).value,
      zip: this.editForm.get(['zip']).value,
      cidade: this.editForm.get(['cidade']).value,
      urf: this.editForm.get(['urf']).value,
      estado: this.editForm.get(['estado']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndereco>>) {
    result.subscribe((res: HttpResponse<IEndereco>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackEstadoById(index: number, item: IEstado) {
    return item.id;
  }
}
