import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DisorderService } from '../service/disorder.service';

import { DisorderComponent } from './disorder.component';

describe('Disorder Management Component', () => {
  let comp: DisorderComponent;
  let fixture: ComponentFixture<DisorderComponent>;
  let service: DisorderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [DisorderComponent],
    })
      .overrideTemplate(DisorderComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DisorderComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(DisorderService);

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
    expect(comp.disorders?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
