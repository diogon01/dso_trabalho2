/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { DsoTrabalho2TestModule } from '../../../test.module';
import { AnuncioHistoricoUpdateComponent } from 'app/entities/anuncio-historico/anuncio-historico-update.component';
import { AnuncioHistoricoService } from 'app/entities/anuncio-historico/anuncio-historico.service';
import { AnuncioHistorico } from 'app/shared/model/anuncio-historico.model';

describe('Component Tests', () => {
  describe('AnuncioHistorico Management Update Component', () => {
    let comp: AnuncioHistoricoUpdateComponent;
    let fixture: ComponentFixture<AnuncioHistoricoUpdateComponent>;
    let service: AnuncioHistoricoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DsoTrabalho2TestModule],
        declarations: [AnuncioHistoricoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnuncioHistoricoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnuncioHistoricoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnuncioHistoricoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnuncioHistorico(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnuncioHistorico();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
