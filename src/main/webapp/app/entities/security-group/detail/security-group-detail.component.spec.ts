import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SecurityGroupDetailComponent } from './security-group-detail.component';

describe('SecurityGroup Management Detail Component', () => {
  let comp: SecurityGroupDetailComponent;
  let fixture: ComponentFixture<SecurityGroupDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SecurityGroupDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ securityGroup: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SecurityGroupDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SecurityGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load securityGroup on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.securityGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
