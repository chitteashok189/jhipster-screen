import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PestControlService } from '../service/pest-control.service';

import { PestControlComponent } from './pest-control.component';

describe('PestControl Management Component', () => {
  let comp: PestControlComponent;
  let fixture: ComponentFixture<PestControlComponent>;
  let service: PestControlService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [PestControlComponent],
    })
      .overrideTemplate(PestControlComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PestControlComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(PestControlService);

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
    expect(comp.pestControls?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
