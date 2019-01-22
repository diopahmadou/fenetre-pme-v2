import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IImf } from 'app/shared/model/imf.model';

type EntityResponseType = HttpResponse<IImf>;
type EntityArrayResponseType = HttpResponse<IImf[]>;

@Injectable({ providedIn: 'root' })
export class ImfService {
    public resourceUrl = SERVER_API_URL + 'api/imfs';

    constructor(protected http: HttpClient) {}

    create(imf: IImf): Observable<EntityResponseType> {
        return this.http.post<IImf>(this.resourceUrl, imf, { observe: 'response' });
    }

    update(imf: IImf): Observable<EntityResponseType> {
        return this.http.put<IImf>(this.resourceUrl, imf, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IImf>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IImf[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
