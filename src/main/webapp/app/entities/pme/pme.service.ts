import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPme } from 'app/shared/model/pme.model';

type EntityResponseType = HttpResponse<IPme>;
type EntityArrayResponseType = HttpResponse<IPme[]>;

@Injectable({ providedIn: 'root' })
export class PmeService {
    public resourceUrl = SERVER_API_URL + 'api/pmes';

    constructor(protected http: HttpClient) {}

    create(pme: IPme): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pme);
        return this.http
            .post<IPme>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(pme: IPme): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(pme);
        return this.http
            .put<IPme>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPme>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPme[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(pme: IPme): IPme {
        const copy: IPme = Object.assign({}, pme, {
            dateCreation: pme.dateCreation != null && pme.dateCreation.isValid() ? pme.dateCreation.format(DATE_FORMAT) : null,
            dateEnregistrement:
                pme.dateEnregistrement != null && pme.dateEnregistrement.isValid() ? pme.dateEnregistrement.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateCreation = res.body.dateCreation != null ? moment(res.body.dateCreation) : null;
            res.body.dateEnregistrement = res.body.dateEnregistrement != null ? moment(res.body.dateEnregistrement) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((pme: IPme) => {
                pme.dateCreation = pme.dateCreation != null ? moment(pme.dateCreation) : null;
                pme.dateEnregistrement = pme.dateEnregistrement != null ? moment(pme.dateEnregistrement) : null;
            });
        }
        return res;
    }
}
