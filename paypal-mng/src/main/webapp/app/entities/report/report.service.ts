import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import { saveAs } from 'file-saver';

import {SERVER_API_URL} from 'app/app.constants';
import {createRequestOption} from 'app/shared';
import {IReport} from 'app/shared/model/report.model';

type EntityResponseType = HttpResponse<IReport>;
type EntityArrayResponseType = HttpResponse<IReport[]>;

@Injectable({providedIn: 'root'})
export class ReportService {
  public resourceUrl = SERVER_API_URL + 'api/reports';

  public uploadUrl = SERVER_API_URL + 'api/file/tracking';

  constructor(protected http: HttpClient) {
  }

  create(report: IReport): Observable<EntityResponseType> {
    return this.http.post<IReport>(this.resourceUrl, report, {observe: 'response'});
  }

  update(report: IReport): Observable<EntityResponseType> {
    return this.http.put<IReport>(this.resourceUrl, report, {observe: 'response'});
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IReport>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReport[]>(this.resourceUrl, {params: options, observe: 'response'});
  }

  uploadTracking(files: Set<File>): Observable<Text> {
    console.log('files ', files);
    files.forEach(file => {
      const formData: FormData = new FormData();
      formData.append('file', file, file.name);
      let headers = new HttpHeaders();
      headers = headers.append('Accept', 'text/csv; charset=utf-8');
      this.http.post(this.uploadUrl, formData, {
        headers,
        responseType: 'text'
      })
        .subscribe(response => {
            console.log('data ', response);
            const newBlob = new Blob([response], { type: 'text/csv' });
            saveAs(newBlob, 'result_' + new Date().toISOString() + '_' + file.name);
          },
          (error => console.log('error', error))
        );
    });
    return;
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, {observe: 'response'});
  }
}
