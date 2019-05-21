import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnuncio } from 'app/shared/model/anuncio.model';
import { AnuncioService } from './anuncio.service';

@Component({
  selector: 'jhi-anuncio-delete-dialog',
  templateUrl: './anuncio-delete-dialog.component.html'
})
export class AnuncioDeleteDialogComponent {
  anuncio: IAnuncio;

  constructor(protected anuncioService: AnuncioService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.anuncioService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'anuncioListModification',
        content: 'Deleted an anuncio'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-anuncio-delete-popup',
  template: ''
})
export class AnuncioDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ anuncio }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AnuncioDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.anuncio = anuncio;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/anuncio', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/anuncio', { outlets: { popup: null } }]);
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
