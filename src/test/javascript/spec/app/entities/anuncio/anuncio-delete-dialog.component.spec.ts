/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { DsoTrabalho2TestModule } from '../../../test.module';
import { AnuncioDeleteDialogComponent } from 'app/entities/anuncio/anuncio-delete-dialog.component';
import { AnuncioService } from 'app/entities/anuncio/anuncio.service';

describe('Component Tests', () => {
  describe('Anuncio Management Delete Component', () => {
    let comp: AnuncioDeleteDialogComponent;
    let fixture: ComponentFixture<AnuncioDeleteDialogComponent>;
    let service: AnuncioService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DsoTrabalho2TestModule],
        declarations: [AnuncioDeleteDialogComponent]
      })
        .overrideTemplate(AnuncioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnuncioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnuncioService);
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
