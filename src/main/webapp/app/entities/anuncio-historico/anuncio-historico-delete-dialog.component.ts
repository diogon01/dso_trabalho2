import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnuncioHistorico } from 'app/shared/model/anuncio-historico.model';
import { AnuncioHistoricoService } from './anuncio-historico.service';

@Component({
  selector: 'jhi-anuncio-historico-delete-dialog',
  templateUrl: './anuncio-historico-delete-dialog.component.html'
})
export class AnuncioHistoricoDeleteDialogComponent {
  anuncioHistorico: IAnuncioHistorico;

  constructor(
    protected anuncioHistoricoService: AnuncioHistoricoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.anuncioHistoricoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'anuncioHistoricoListModification',
        content: 'Deleted an anuncioHistorico'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-anuncio-historico-delete-popup',
  template: ''
})
export class AnuncioHistoricoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ anuncioHistorico }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AnuncioHistoricoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.anuncioHistorico = anuncioHistorico;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/anuncio-historico', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/anuncio-historico', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
