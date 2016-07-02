/*
ID: Xu Yan
LANG: C
TASK: inflate
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

int main() {
	typedef struct
	{
		int required_minutes;
		int score;
	} problem_class;

	int contest_minutes, problem_class_count;
	problem_class classes[10000];

	FILE *fin, *fout;
	fin = fopen("inflate.in", "r");
	fout = fopen("inflate.out", "w");
	assert(fin != NULL && fout != NULL);
	fscanf(fin, "%d %d\n", &contest_minutes, &problem_class_count);

	int i;
	for (i = 0; i < problem_class_count; i++) {
		fscanf(fin, "%d %d\n", &classes[i].score, &classes[i].required_minutes);
	}
	fclose(fin);

	int allowed_minutes;
	int answer = 0;
	int max_scores[contest_minutes + 1];
	for (i = 0; i <= contest_minutes; i++) {
		max_scores[i] = 0;
	}
	for (allowed_minutes = 1; allowed_minutes <= contest_minutes; allowed_minutes++) {
		int max_score_in_allowed_minutes = 0;
		int class_index;
		for (class_index = 0; class_index < problem_class_count; class_index++) {
			int sub = allowed_minutes - classes[class_index].required_minutes;
			if (sub >= 0) {
				if (max_score_in_allowed_minutes < max_scores[sub] + classes[class_index].score) {
					max_score_in_allowed_minutes = max_scores[sub] + classes[class_index].score;
				}
			}
		}
		max_scores[allowed_minutes] = max_score_in_allowed_minutes;
		if (max_score_in_allowed_minutes > answer) {
			answer = max_score_in_allowed_minutes;
		}
	}

	fprintf(fout, "%d\n", answer);
	fclose(fout);

	exit(0);
}
