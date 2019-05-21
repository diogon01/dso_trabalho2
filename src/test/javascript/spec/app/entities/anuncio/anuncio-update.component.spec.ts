/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { DsoTrabalho2TestModule } from '../../../test.module';
import { AnuncioUpdateComponent } from 'app/entities/anuncio/anuncio-update.component';
import { AnuncioService } from 'app/entities/anuncio/anuncio.service';
import { Anuncio } from 'app/shared/model/anuncio.model';

describe('Component Tests', () => {
  describe('Anuncio Management Update Component', () => {
    let comp: AnuncioUpdateComponent;
    let fixture: ComponentFixture<AnuncioUpdateComponent>;
    let service: AnuncioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DsoTrabalho2TestModule],
        declarations: [AnuncioUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AnuncioUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnuncioUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnuncioService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Anuncio(123);
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
        const entity = new Anuncio();
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
