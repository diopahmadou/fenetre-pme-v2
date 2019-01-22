import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pret } from 'app/shared/model/pret.model';
import { PretService } from './pret.service';
import { PretComponent } from './pret.component';
import { PretDetailComponent } from './pret-detail.component';
import { PretUpdateComponent } from './pret-update.component';
import { PretDeletePopupComponent } from './pret-delete-dialog.component';
import { IPret } from 'app/shared/model/pret.model';

@Injectable({ providedIn: 'root' })
export class PretResolve implements Resolve<IPret> {
    constructor(private service: PretService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Pret> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Pret>) => response.ok),
                map((pret: HttpResponse<Pret>) => pret.body)
            );
        }
        return of(new Pret());
    }
}

export const pretRoute: Routes = [
    {
        path: 'pret',
        component: PretComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Prets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pret/:id/view',
        component: PretDetailComponent,
        resolve: {
            pret: PretResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pret/new',
        component: PretUpdateComponent,
        resolve: {
            pret: PretResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prets'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'pret/:id/edit',
        component: PretUpdateComponent,
        resolve: {
            pret: PretResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prets'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pretPopupRoute: Routes = [
    {
        path: 'pret/:id/delete',
        component: PretDeletePopupComponent,
        resolve: {
            pret: PretResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Prets'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
