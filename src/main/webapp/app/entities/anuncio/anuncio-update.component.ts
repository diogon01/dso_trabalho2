import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAnuncio, Anuncio } from 'app/shared/model/anuncio.model';
import { AnuncioService } from './anuncio.service';
import { IVenda } from 'app/shared/model/venda.model';
import { VendaService } from 'app/entities/venda';
import { IEndereco } from 'app/shared/model/endereco.model';
import { EnderecoService } from 'app/entities/endereco';

@Component({
  selector: 'jhi-anuncio-update',
  templateUrl: './anuncio-update.component.html'
})
export class AnuncioUpdateComponent implements OnInit {
  anuncio: IAnuncio;
  isSaving: boolean;

  titulovendas: IVenda[];

  enderecos: IEndereco[];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    valorMin: [],
    valorMax: [],
    categoria: [],
    tituloVenda: [],
    endereco: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected anuncioService: AnuncioService,
    protected vendaService: VendaService,
    protected enderecoService: EnderecoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ anuncio }) => {
      this.updateForm(anuncio);
      this.anuncio = anuncio;
    });
    this.vendaService
      .query({ filter: 'job-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IVenda[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVenda[]>) => response.body)
      )
      .subscribe(
        (res: IVenda[]) => {
          if (!this.anuncio.tituloVenda || !this.anuncio.tituloVenda.id) {
            this.titulovendas = res;
          } else {
            this.vendaService
              .find(this.anuncio.tituloVenda.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IVenda>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IVenda>) => subResponse.body)
              )
              .subscribe(
                (subRes: IVenda) => (this.titulovendas = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.enderecoService
      .query({ filter: 'anuncio-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IEndereco[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEndereco[]>) => response.body)
      )
      .subscribe(
        (res: IEndereco[]) => {
          if (!this.anuncio.endereco || !this.anuncio.endereco.id) {
            this.enderecos = res;
          } else {
            this.enderecoService
              .find(this.anuncio.endereco.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IEndereco>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IEndereco>) => subResponse.body)
              )
              .subscribe(
                (subRes: IEndereco) => (this.enderecos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(anuncio: IAnuncio) {
    this.editForm.patchValue({
      id: anuncio.id,
      titulo: anuncio.titulo,
      valorMin: anuncio.valorMin,
      valorMax: anuncio.valorMax,
      categoria: anuncio.categoria,
      tituloVenda: anuncio.tituloVenda,
      endereco: anuncio.endereco
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const anuncio = this.createFromForm();
    if (anuncio.id !== undefined) {
      this.subscribeToSaveResponse(this.anuncioService.update(anuncio));
    } else {
      this.subscribeToSaveResponse(this.anuncioService.create(anuncio));
    }
  }

  private createFromForm(): IAnuncio {
    const entity = {
      ...new Anuncio(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      valorMin: this.editForm.get(['valorMin']).value,
      valorMax: this.editForm.get(['valorMax']).value,
      categoria: this.editForm.get(['categoria']).value,
      tituloVenda: this.editForm.get(['tituloVenda']).value,
      endereco: this.editForm.get(['endereco']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnuncio>>) {
    result.subscribe((res: HttpResponse<IAnuncio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackVendaById(index: number, item: IVenda) {
    return item.id;
  }

  trackEnderecoById(index: number, item: IEndereco) {
    return item.id;
  }
}
