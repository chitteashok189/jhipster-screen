import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { InspectionRecordService } from '../service/inspection-record.service';

import { InspectionRecordComponent } from './inspection-record.component';

describe('InspectionRecord Management Component', () => {
  let comp: InspectionRecordComponent;
  let fixture: ComponentFixture<InspectionRecordComponent>;
  let service: InspectionRecordService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [InspectionRecordComponent],
    })
      .overrideTemplate(InspectionRecordComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(InspectionRecordComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(InspectionRecordService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.inspectionRecords?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
