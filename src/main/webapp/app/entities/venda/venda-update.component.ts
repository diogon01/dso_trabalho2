import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IVenda, Venda } from 'app/shared/model/venda.model';
import { VendaService } from './venda.service';
import { IAnuncio } from 'app/shared/model/anuncio.model';
import { AnuncioService } from 'app/entities/anuncio';

@Component({
  selector: 'jhi-venda-update',
  templateUrl: './venda-update.component.html'
})
export class VendaUpdateComponent implements OnInit {
  venda: IVenda;
  isSaving: boolean;

  anuncios: IAnuncio[];

  editForm = this.fb.group({
    id: [],
    titulo: [],
    resumo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vendaService: VendaService,
    protected anuncioService: AnuncioService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ venda }) => {
      this.updateForm(venda);
      this.venda = venda;
    });
    this.anuncioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAnuncio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAnuncio[]>) => response.body)
      )
      .subscribe((res: IAnuncio[]) => (this.anuncios = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(venda: IVenda) {
    this.editForm.patchValue({
      id: venda.id,
      titulo: venda.titulo,
      resumo: venda.resumo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const venda = this.createFromForm();
    if (venda.id !== undefined) {
      this.subscribeToSaveResponse(this.vendaService.update(venda));
    } else {
      this.subscribeToSaveResponse(this.vendaService.create(venda));
    }
  }

  private createFromForm(): IVenda {
    const entity = {
      ...new Venda(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      resumo: this.editForm.get(['resumo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenda>>) {
    result.subscribe((res: HttpResponse<IVenda>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
