import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main5 {
	 static boolean is_right_overtaking_need(int j,car[] eachcar,int rightbefore,int []danger_times,int []not_danger_times){
		 double safety_dist=eachcar[j].sd+10;
		 double danger_dist=eachcar[j].sd;
		 if(rightbefore!=-1){
			 if(eachcar[rightbefore].dist-eachcar[j].dist<=safety_dist&&eachcar[rightbefore].v<eachcar[j].want_v){
				 if(eachcar[rightbefore].dist-eachcar[j].dist<danger_dist&&eachcar[j].lane==1){
					 //System.out.println("Σ�գ�");
					 danger_times[0]++;
				 }else{
					 not_danger_times[0]++;
				 }
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 static boolean is_left_overtaking_need(int j,car[] eachcar,int leftbefore,int []danger_times,int []not_danger_times){
		 double safety_dist=eachcar[j].sd+10;
		 double danger_dist=eachcar[j].sd;
		 if(leftbefore!=-1){
			 if(eachcar[leftbefore].dist-eachcar[j].dist<=safety_dist&&eachcar[leftbefore].v<eachcar[j].want_v){
				 if(eachcar[leftbefore].dist-eachcar[j].dist<danger_dist&&eachcar[j].lane==1){
					 //System.out.println("Σ�գ�");
					 danger_times[0]++;
				 }else{
					 not_danger_times[0]++;
				 }
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 static boolean is_to_left(int j,car[] eachcar,int carnum,int outcar,int leftbefore,int leftafter){
		 double safety_dist=eachcar[j].sd+10;
		 if(leftbefore==-1||eachcar[leftbefore].dist-eachcar[j].dist>safety_dist){
			 if(leftafter==-1||eachcar[j].dist-eachcar[leftafter].dist-eachcar[leftafter].v>safety_dist){
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 static boolean is_to_right(int j,car[] eachcar,int carnum,int outcar,int rightbefore,int rightafter){
		 double safety_dist=eachcar[j].sd+10;
		 if(rightbefore==-1||eachcar[rightbefore].dist-eachcar[j].dist>safety_dist){
			 if(rightafter==-1||eachcar[j].dist-eachcar[rightafter].dist-eachcar[rightafter].v>safety_dist){
				 return true;
			 }
			 return false;
		 }
		 return false;
	 }
	 /*static int is_left_following(int j,car[] eachcar,int carnum,int outcar,int []danger_times,int [] not_danger_times){
		 double danger_dist=eachcar[j].sd;
		 double safety_dist=eachcar[j].sd+10;
		 int leftbefore=-1;//��¼���ǰ��
		 for(int s=j-1;s>=outcar;s--){
			 if(eachcar[s].lane==2){
				 leftbefore=s;//ǰ���г�
				 break;
			 }
		 }
		 if(leftbefore==-1||eachcar[leftbefore].dist-eachcar[j].dist>safety_dist){
			 return 0;
		 }else{
			 if(eachcar[leftbefore].dist-eachcar[j].dist<danger_dist){
				 danger_times[0]++;
				 //System.out.println("Σ�գ�");
			 }else{
				 not_danger_times[0]++;
			 }
			 return eachcar[leftbefore].v;
		 }
		 
	 }*/
	static double gaussrand(double E, double V) {
	    	double V1, V2 = 0, S = 0;
	    	int phase = 0;
	    	double X;
	    	Random r = new Random();
	    	if (phase == 0) {
	    		do {
	    			double U1 = (double)r.nextDouble();
	    			double U2 = (double)r.nextDouble();
	    			V1 = 2 * U1 - 1;
	    			V2 = 2 * U2 - 1;
	    			S = V1 * V1 + V2 * V2;
	    		} while (S >= 1 || S == 0);
	    		X = V1 * Math.sqrt(-2 * Math.log(S) / S);
	    	}
	    	else X = V2 * Math.sqrt(-2 * Math.log(S) / S);
	    	phase = 1 - phase;
	    	return X * V + E;
	 }
	 public static void main(String[] args){
		 int carnum=0;
		 int outcar=0;//��ʻ���ĳ���
		 int highway_length=8000;//8km
		 //int safety_dist=100;//100m
		 int max_car_num=2000;
		 int overtaking_dist=800;
		 car eachcar[]=new car[max_car_num];
		 int left_v=33;//��೵���ٶ�
		 int overtake_num=0;//��¼��������
		 int return_num=0;
		 double is_busy=0.05;
		 int danger_times[]={0};
		 int  not_danger_times[]={0};
		 int left_out=0;
		 //int danger_dist=40;//Σ�վ���40m
		 int left_in=0;
		 int right_in=0;
		 double beta=0.1;
		 double gama=0.8;
		 double mu=12;
		 double std=3;
		 for(int i=0;i<300;i++){//ʱ��
			 Random r = new Random();
			 double u = r.nextDouble();
			 if(u<is_busy&&(carnum==0||eachcar[carnum-1].dist>eachcar[carnum-1].sd)){
				 eachcar[carnum]=new car();
				 eachcar[carnum].dist=0;
				 if(r.nextDouble()<0.55){
					 eachcar[carnum].lane=1; 
					 right_in++;
				 }else{
					 eachcar[carnum].lane=2; 
					 left_in++;
				 }
				 //eachcar[carnum].v=(int)Math.floor(r.nextDouble()*16)+17;
				 eachcar[carnum].v=gaussrand((double)25,2.8);
				 eachcar[carnum].want_v=eachcar[carnum].v;
				 eachcar[carnum].i=carnum;
				 eachcar[carnum].tr=r.nextDouble();
				 eachcar[carnum].sd=eachcar[carnum].tr*eachcar[carnum].v+0.63828*eachcar[carnum].v*eachcar[carnum].v;
				 eachcar[carnum].time=i;
				 eachcar[carnum].P_danger=0;
				 //System.out.println(carnum+"�ٶ�:"+eachcar[carnum].v);
				 carnum+=1;
			 }
			 for(int j=outcar;j<carnum;j++){//�Ŷӵĳ�
				 int rightbefore=-1;//��¼�Ҳ�ǰ��
				 int leftbefore=-1;//��¼���ǰ��
				 int rightafter=-1;//��¼�Ҳ�ǰ��
				 int leftafter=-1;//��¼���ǰ��
				 for(int s=j-1;s>=outcar;s--){
					 if(eachcar[s].lane==1&&rightbefore==-1){
						 rightbefore=s;//ǰ���г�
					 }else if(eachcar[s].lane==2&&leftbefore==-1){
						 leftbefore=s;//ǰ���г�
					 }
					 if(rightbefore!=-1&&leftbefore!=-1){
						 break;
					 }
				 }
				 for(int s=j+1;s<carnum;s++){
					 if(eachcar[s].lane==1&&rightafter==-1){
						 rightafter=s;//���г�
					 }
					 else if(eachcar[s].lane==2&&leftafter==-1){
						 leftafter=s;//ǰ���г�
					 }
					 if(rightbefore!=-1&&leftbefore!=-1){
						 break;
					 }
				 }
				 double left_alpha=0,right_alpha=0;
				 if(eachcar[j].lane==1){
					 eachcar[j].dist+=eachcar[j].v;//�Ҳ೵�� 
					 if(eachcar[j].dist>highway_length){
						 outcar++;
						 eachcar[j].dist=-1;
						 eachcar[j].time=i-eachcar[j].time;
						 if(eachcar[j].lane==2){left_out++;}
						 continue;
					 }
					 if(j!=0){
						 if(is_right_overtaking_need(j,eachcar,rightbefore,danger_times, not_danger_times)){
							 if(is_to_left(j,eachcar,carnum,outcar,leftbefore,leftafter)){
								 eachcar[j].lane=2;
								 eachcar[j].v=eachcar[j].v+6;
								 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.63828*eachcar[j].v*eachcar[j].v;
								 overtake_num++;
								 //System.out.println("��"+i+"��"+eachcar[j].i+"����");
							 }else{
								 if(rightbefore!=-1){
									 eachcar[j].v = eachcar[rightbefore].v;
									 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.63828*eachcar[j].v*eachcar[j].v;
								 }
							 }
						 }else{
							 eachcar[j].v=eachcar[j].want_v;
							 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.63828*eachcar[j].v*eachcar[j].v;
						 }
					 }
					 if(rightafter!=-1){
						 right_alpha=gaussrand(mu,std)*Math.sqrt(eachcar[rightafter].v); 
					 }
					 if(leftafter!=-1){
						 left_alpha=gaussrand(mu,std)*Math.sqrt(eachcar[leftafter].v);  
					 }
					 double P0i,P1i,P2i,P3i,P4i;
					 P0i=Math.pow(((double)eachcar[j].v/75), 4);
					 if(rightbefore==-1){
						 P1i=0;
				     }else{
				    	 P1i=beta*(1-((double)(eachcar[rightbefore].dist-eachcar[j].dist)/(double)eachcar[j].sd))*Math.pow(((double)eachcar[rightbefore].v/75), 4);
				    	 if(P1i<0){P1i=0;}
				     }
					 if(leftbefore==-1){
						 P2i=0;
				     }else{
				    	 P2i=beta*(1-((double)(eachcar[leftbefore].dist-eachcar[j].dist)/(double)eachcar[j].sd))*Math.pow(((double)eachcar[leftbefore].v/75), 4);
				    	 if(P2i<0){P2i=0;}
				     }
					 if(leftafter==-1){
						 P3i=0;
				     }else{
				    	 P3i=gama*(1-((double)(eachcar[j].dist-eachcar[j].v-eachcar[leftafter].dist)/left_alpha))*Math.pow(((double)eachcar[leftafter].v/75), 4);
				    	 if(P3i<0){P3i=0;}
				     }
					 if(Double.isNaN(P3i)){System.out.println("leftafter:"+leftafter+"dist:"+(eachcar[j].dist-eachcar[j].v-eachcar[leftafter].dist)+"alpha:"+left_alpha);}
					 if(rightafter==-1){
						 P4i=0;
				     }else{
				    	 P4i=gama*(1-((double)(eachcar[j].dist-eachcar[j].v-eachcar[rightafter].dist)/right_alpha))*Math.pow(((double)eachcar[rightafter].v/75), 4);
				    	 if(P4i<0){P4i=0;}
				     }
					 double P=(P0i+P1i+P2i+P3i+P4i)-(P0i*P1i+P0i*P2i+P0i*P3i+P0i*P4i+P1i*P2i+P1i*P3i+P1i*P4i+P2i*P3i+P2i*P4i+P3i*P4i)
					 +(P0i*P1i*P2i+P0i*P1i*P3i+P0i*P1i*P4i+P0i*P2i*P3i+P0i*P2i*P4i+P0i*P3i*P4i+P1i*P2i*P3i+P1i*P2i*P4i+P1i*P3i*P4i+P2i*P3i*P4i)
					 -(P0i*P1i*P2i*P3i+P0i*P1i*P2i*P4i+P0i*P1i*P3i*P4i+P0i*P2i*P3i*P4i+P1i*P2i*P3i*P4i)+P0i*P1i*P2i*P3i*P4i;
					 eachcar[j].P_danger+=P;
				 }else{
					 eachcar[j].dist+=left_v;//���
					 if(eachcar[j].dist>highway_length){
						 outcar++;
						 eachcar[j].dist=-1;
						 eachcar[j].time=i-eachcar[j].time;
						 if(eachcar[j].lane==2){left_out++;}
						 continue;
					 }
					 if(is_left_overtaking_need(j,eachcar,leftbefore,danger_times, not_danger_times)){
						 if(is_to_right(j,eachcar,carnum,outcar,rightbefore,rightafter)){
							 eachcar[j].lane=1;
							 eachcar[j].v=eachcar[j].v+6;
							 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.63828*eachcar[j].v*eachcar[j].v;
							 overtake_num++;
							 //System.out.println("��"+i+"��"+eachcar[j].i+"����");
						 }else{
							 if(leftbefore!=-1){
								 eachcar[j].v = eachcar[leftbefore].v;
								 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.63828*eachcar[j].v*eachcar[j].v;
							 }
						 }
					 }else{
						 eachcar[j].v=eachcar[j].want_v;
						 eachcar[j].sd=eachcar[j].tr*eachcar[j].v+0.63828*eachcar[j].v*eachcar[j].v;
					 }
					 if(leftafter!=-1){
						 left_alpha=gaussrand(mu,std)*Math.sqrt(eachcar[leftafter].v); 
					 }
					 if(rightafter!=-1){
						 right_alpha=gaussrand(mu,std)*Math.sqrt(eachcar[rightafter].v); 
					 }
					 double P0i,P1i,P2i,P3i,P4i;
					 P0i=Math.pow(((double)eachcar[j].v/75), 4);
					 if(rightbefore==-1){
						 P2i=0;
				     }else{
				    	 P2i=beta*(1-((double)(eachcar[rightbefore].dist-eachcar[j].dist)/(double)eachcar[j].sd))*Math.pow(((double)eachcar[rightbefore].v/75), 4);
				    	 if(P2i<0){P2i=0;}
				     }
					 if(leftbefore==-1){
						 P1i=0;
				     }else{
				    	 P1i=beta*(1-((double)(eachcar[leftbefore].dist-eachcar[j].dist)/(double)eachcar[j].sd))*Math.pow(((double)eachcar[leftbefore].v/75), 4);
				    	 if(P1i<0){P1i=0;}
				     }
					 if(leftafter==-1){
						 P4i=0;
				     }else{
				    	 P4i=gama*(1-((double)(eachcar[j].dist-eachcar[j].v-eachcar[leftafter].dist)/left_alpha))*Math.pow(((double)eachcar[leftafter].v/75), 4);
				    	 if(P4i<0){P4i=0;}
				     }
					 if(rightafter==-1){
						 P3i=0;
				     }else{
				    	 P3i=gama*(1-((double)(eachcar[j].dist-eachcar[j].v-eachcar[rightafter].dist)/right_alpha))*Math.pow(((double)eachcar[rightafter].v/75), 4);
				    	 if(P3i<0){P3i=0;}
				     }
					 double P=(P0i+P1i+P2i+P3i+P4i)-(P0i*P1i+P0i*P2i+P0i*P3i+P0i*P4i+P1i*P2i+P1i*P3i+P1i*P4i+P2i*P3i+P2i*P4i+P3i*P4i)
					 +(P0i*P1i*P2i+P0i*P1i*P3i+P0i*P1i*P4i+P0i*P2i*P3i+P0i*P2i*P4i+P0i*P3i*P4i+P1i*P2i*P3i+P1i*P2i*P4i+P1i*P3i*P4i+P2i*P3i*P4i)
					 -(P0i*P1i*P2i*P3i+P0i*P1i*P2i*P4i+P0i*P1i*P3i*P4i+P0i*P2i*P3i*P4i+P1i*P2i*P3i*P4i)+P0i*P1i*P2i*P3i*P4i;
					 eachcar[j].P_danger+=P;
				 }
				 Comparator cmp = new myComparator();
			     Arrays.sort(eachcar,outcar,carnum,cmp);
			 }
		 }
		 double time_ave=0,danger_ave=0;
		 for(int i=0;i<outcar;i++){
			 time_ave+=eachcar[i].time;
			 eachcar[i].P_danger=eachcar[i].P_danger/(double)eachcar[i].time;
			 //System.out.println("P_danger:"+eachcar[i].P_danger+"time:"+eachcar[i].time);
			 danger_ave+=eachcar[i].P_danger;
			 //System.out.println("��"+i+"������Σ�ո���Ϊ��"+ eachcar[i].P_danger);
		 }
		 time_ave=time_ave/outcar;
		 danger_ave=danger_ave/outcar;
		 System.out.println("���г�������"+carnum);
		 System.out.println("�����"+left_in);
		 System.out.println("�ҽ���"+right_in);
		 System.out.println("����������"+overtake_num);
		 //System.out.println("�������ȥ�Ĵ�����"+return_num);
		 System.out.println("��ʻ����ͷ(��������)��������"+outcar);
		 //System.out.println("�󳵵���ȥ��������"+left_out);
		 //System.out.println("Σ�ձ�����"+(float)danger_times[0]/(float)(not_danger_times[0]+danger_times[0]));
		 System.out.println("������θ��ٵ�ƽ��ʱ�䣺"+time_ave); 
		 System.out.println("������θ��ٵ�ƽ��Σ�ո���Ϊ��"+danger_ave); 
	 }
}
