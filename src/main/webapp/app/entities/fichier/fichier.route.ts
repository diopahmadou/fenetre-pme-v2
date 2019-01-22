import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fichier } from 'app/shared/model/fichier.model';
import { FichierService } from './fichier.service';
import { FichierComponent } from './fichier.component';
import { FichierDetailComponent } from './fichier-detail.component';
import { FichierUpdateComponent } from './fichier-update.component';
import { FichierDeletePopupComponent } from './fichier-delete-dialog.component';
import { IFichier } from 'app/shared/model/fichier.model';

@Injectable({ providedIn: 'root' })
export class FichierResolve implements Resolve<IFichier> {
    constructor(private service: FichierService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Fichier> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Fichier>) => response.ok),
                map((fichier: HttpResponse<Fichier>) => fichier.body)
            );
        }
        return of(new Fichier());
    }
}

export const fichierRoute: Routes = [
    {
        path: 'fichier',
        component: FichierComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Fichiers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fichier/:id/view',
        component: FichierDetailComponent,
        resolve: {
            fichier: FichierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fichiers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fichier/new',
        component: FichierUpdateComponent,
        resolve: {
            fichier: FichierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fichiers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'fichier/:id/edit',
        component: FichierUpdateComponent,
        resolve: {
            fichier: FichierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fichiers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fichierPopupRoute: Routes = [
    {
        path: 'fichier/:id/delete',
        component: FichierDeletePopupComponent,
        resolve: {
            fichier: FichierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Fichiers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
