import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPret } from 'app/shared/model/pret.model';

type EntityResponseType = HttpResponse<IPret>;
type EntityArrayResponseType = HttpResponse<IPret[]>;

@Injectable({ providedIn: 'root' })
export class PretService {
    public resourceUrl = SERVER_API_URL + 'api/prets';

    constructor(protected http: HttpClient) {}

    create(pret: IPret): Observable<EntityResponseType> {
        return this.http.post<IPret>(this.resourceUrl, pret, { observe: 'response' });
    }

    update(pret: IPret): Observable<EntityResponseType> {
        return this.http.put<IPret>(this.resourceUrl, pret, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IPret>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IPret[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
