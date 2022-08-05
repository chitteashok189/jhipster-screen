import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ScoutingService } from '../service/scouting.service';

import { ScoutingComponent } from './scouting.component';

describe('Scouting Management Component', () => {
  let comp: ScoutingComponent;
  let fixture: ComponentFixture<ScoutingComponent>;
  let service: ScoutingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [ScoutingComponent],
    })
      .overrideTemplate(ScoutingComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ScoutingComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ScoutingService);

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
    expect(comp.scoutings?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
