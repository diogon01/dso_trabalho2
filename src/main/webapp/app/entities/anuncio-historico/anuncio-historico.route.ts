import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AnuncioHistorico } from 'app/shared/model/anuncio-historico.model';
import { AnuncioHistoricoService } from './anuncio-historico.service';
import { AnuncioHistoricoComponent } from './anuncio-historico.component';
import { AnuncioHistoricoDetailComponent } from './anuncio-historico-detail.component';
import { AnuncioHistoricoUpdateComponent } from './anuncio-historico-update.component';
import { AnuncioHistoricoDeletePopupComponent } from './anuncio-historico-delete-dialog.component';
import { IAnuncioHistorico } from 'app/shared/model/anuncio-historico.model';

@Injectable({ providedIn: 'root' })
export class AnuncioHistoricoResolve implements Resolve<IAnuncioHistorico> {
  constructor(private service: AnuncioHistoricoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAnuncioHistorico> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AnuncioHistorico>) => response.ok),
        map((anuncioHistorico: HttpResponse<AnuncioHistorico>) => anuncioHistorico.body)
      );
    }
    return of(new AnuncioHistorico());
  }
}

export const anuncioHistoricoRoute: Routes = [
  {
    path: '',
    component: AnuncioHistoricoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dsoTrabalho2App.anuncioHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AnuncioHistoricoDetailComponent,
    resolve: {
      anuncioHistorico: AnuncioHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dsoTrabalho2App.anuncioHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AnuncioHistoricoUpdateComponent,
    resolve: {
      anuncioHistorico: AnuncioHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dsoTrabalho2App.anuncioHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AnuncioHistoricoUpdateComponent,
    resolve: {
      anuncioHistorico: AnuncioHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dsoTrabalho2App.anuncioHistorico.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const anuncioHistoricoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AnuncioHistoricoDeletePopupComponent,
    resolve: {
      anuncioHistorico: AnuncioHistoricoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'dsoTrabalho2App.anuncioHistorico.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
