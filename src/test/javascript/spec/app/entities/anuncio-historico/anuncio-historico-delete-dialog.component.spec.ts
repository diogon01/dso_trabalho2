/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DsoTrabalho2TestModule } from '../../../test.module';
import { AnuncioHistoricoDeleteDialogComponent } from 'app/entities/anuncio-historico/anuncio-historico-delete-dialog.component';
import { AnuncioHistoricoService } from 'app/entities/anuncio-historico/anuncio-historico.service';

describe('Component Tests', () => {
  describe('AnuncioHistorico Management Delete Component', () => {
    let comp: AnuncioHistoricoDeleteDialogComponent;
    let fixture: ComponentFixture<AnuncioHistoricoDeleteDialogComponent>;
    let service: AnuncioHistoricoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DsoTrabalho2TestModule],
        declarations: [AnuncioHistoricoDeleteDialogComponent]
      })
        .overrideTemplate(AnuncioHistoricoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnuncioHistoricoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnuncioHistoricoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
