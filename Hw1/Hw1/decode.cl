; *********************************************
; *  341  Programming Languages               *
; *  Fall 2016                                *
; *  Author: Şahin Eğilmez 131044059          *
; *********************************************

;; ENVIRONMENT
;; "c2i, "i2c",and "apply-list"
(load "include.cl")

;; test document
(load "document.cl")

;; test-dictionary
;; this is needed for spell checking
(load "test-dictionary.cl")

;;(load "dictionary.cl") ;;  real dictionary (45K words)
(defparameter *alphabet* '((a (Nil 0)) (b (Nil 0)) (c (Nil 0)) (d (Nil 0)) (e (Nil 0)) (f (Nil 0)) (g (Nil 0)) (h (Nil 0)) (i (Nil 0)) (j (Nil 0)) 
	(k (Nil 0)) (l (Nil 0)) (m (Nil 0)) (n (Nil 0)) (o (Nil 0))  (p (Nil 0)) (q (Nil 0)) (r (Nil 0)) (s (Nil 0)) (t (Nil 0)) (u (Nil 0)) (v (Nil 0)) (w (Nil 0)) (x (Nil 0)) (y (Nil 0) ) (z (Nil 0) )))
;; -----------------------------------------------------
;; HELPERS
;; *** PLACE YOUR HELPER FUNCTIONS BELOW ***
;change nth list of l to new
(defun change-list (l n new) 
	(setf (nth n l) new)
	)
;check spell by spell in dictionary
(defun spell-checker-0 (word )
  ;function check word is dictionary
	(dolist (x *dictionary*) 
		(if(equal x word)
				(return-from spell-checker-0 t)
				)
		)
	(return-from spell-checker-0 nil)		
)
;encode word
(defun encode-w (word n)
	(let ((o ()) (i 0))
	(dolist (ch word)
		(push (i2c(mod (+ (c2i ch) n) 26)) o) 
		(incf i 1))
	(return-from encode-w (reverse o))
	)
)
;encode paragraph
(defun encode-p (paragraph n)
	(let ((o ()) (i 0))
	(dolist (w paragraph)
		(push (encode-w w n) o) 
		(incf i 1))
	(return-from encode-p (reverse o))
	)
)
;encode document
(defun encode-d (document n)
	(let ((o ()) (i 0))
	(dolist (p document)
		(push (encode-p p n) o) 
		(incf i 1))
	(return-from encode-d (reverse o))
	)
)
;convert word to code. For example "hello" : "01224"
(defun convert-num (word)
	(let ((o ()) (i 0) (k 0))
	(dolist (ch word)
		(block nested-loop
		(do ((j 0 (1+ j)))
 			((< i j))
			(if (equal (nth j word) ch )
				(progn (push j o) (setq k 1) (return-from nested-loop))
				 )))
		(if (= k 1)
			(setq k 0)
			(push i o)
			)
		(incf i 1)
		)
	(return-from convert-num (reverse o))
	)
)
;set each possible letter
(defun set-letter (cdr-letter encoded-letter )
	(dolist (cl cdr-letter)
	(if (equal cl '(Nil 0))
		(progn (change-list cl 0 encoded-letter )
		 (change-list cl 1 1 ) (return-from set-letter cdr-letter))  
		)
	(if (equal (nth 0 cl) encoded-letter)
		(progn (change-list cl 1 ( + (nth 1 cl) 1) ) (return-from set-letter cdr-letter)) 
		 )
	)
	(setq lst (copy-list '(x 1)))
	(push lst (cdr (last cdr-letter)))
	(change-list (nth 0 (last cdr-letter)) 0 encoded-letter)
	(return-from set-letter cdr-letter)
)
;analys encoded word and set *alphabet*
(defun analysis-and-set(encoded decoded)
	(let ((i 0))
	(dolist (e encoded)
		(dolist (letter *alphabet*)
			(if (equal (car letter) e)
				(progn  (set-letter (cdr letter) (nth i decoded)) (incf i) )
				)
			)
		)
	)
)
;translate document by *alphabet*
(defun translate (doc)
	(dolist (p doc)
		(dolist (w p)
			(let ((k 0))
			(dolist (ch w)
				(dolist (letter *alphabet*)
					(if (equal (car letter) ch)
						(let ((i 0))
							(dolist (l (cdr letter))
								(if (> (nth 1 l) i)
									(progn (setf i (nth 1 l))  (change-list w k (nth 0 l)) )
									)
								)
							)
						)
					)
				(incf k)
				)
			)
			)
		)
	(return-from translate doc)
	)

;; -----------------------------------------------------
;; DECODE FUNCTIONS
;decode paraghaph spell by spell and set *alphabet*
(defun Gen-Decoder-A (paragraph)
  (dolist (w paragraph)
  	(dolist (d *dictionary*)
  	(if (equal (convert-num w) (convert-num d)) 
  		(analysis-and-set w d)
  		)
  		)
  	)
)

(defun Gen-Decoder-B-0 (paragraph)
  ;you should implement this function
)

(defun Gen-Decoder-B-1 (paragraph)
  ;you should implement this function
)
;decode document using decoder
(defun Code-Breaker (document decoder)
  (dolist (p document)
  	(funcall decoder p )
  	)
  (return-from Code-Breaker (translate document ))
)
;-----------------------------------------------------
(print (encode-d *test-document* 3))
(print(Code-Breaker (encode-d *test-document* 3) #'Gen-Decoder-A))

