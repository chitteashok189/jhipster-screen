import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PartyClassificationService } from '../service/party-classification.service';

import { PartyClassificationComponent } from './party-classification.component';

describe('PartyClassification Management Component', () => {
  let comp: PartyClassificationComponent;
  let fixture: ComponentFixture<PartyClassificationComponent>;
  let service: PartyClassificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PartyClassificationComponent],
    })
      .overrideTemplate(PartyClassificationComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PartyClassificationComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PartyClassificationService);

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
    expect(comp.partyClassifications?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
