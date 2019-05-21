import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Anuncio } from 'app/shared/model/anuncio.model';
import { AnuncioService } from './anuncio.service';
import { AnuncioComponent } from './anuncio.component';
import { AnuncioDetailComponent } from './anuncio-detail.component';
import { AnuncioUpdateComponent } from './anuncio-update.component';
import { AnuncioDeletePopupComponent } from './anuncio-delete-dialog.component';
import { IAnuncio } from 'app/shared/model/anuncio.model';

@Injectable({ providedIn: 'root' })
export class AnuncioResolve implements Resolve<IAnuncio> {
  constructor(private service: AnuncioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnuncio> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Anuncio>) => response.ok),
        map((anuncio: HttpResponse<Anuncio>) => anuncio.body)
      );
    }
    return of(new Anuncio());
  }
}

export const anuncioRoute: Routes = [
  {
    path: '',
    component: AnuncioComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'dsoTrabalho2App.anuncio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnuncioDetailComponent,
    resolve: {
      anuncio: AnuncioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dsoTrabalho2App.anuncio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnuncioUpdateComponent,
    resolve: {
      anuncio: AnuncioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dsoTrabalho2App.anuncio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnuncioUpdateComponent,
    resolve: {
      anuncio: AnuncioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dsoTrabalho2App.anuncio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const anuncioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AnuncioDeletePopupComponent,
    resolve: {
      anuncio: AnuncioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dsoTrabalho2App.anuncio.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
