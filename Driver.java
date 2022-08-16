
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Driver extends Application {
	final static Font font3 = Font.font("Times New Roman", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20);
	// buttons layout template
	private static final String[][] template = { { "sin", "cos", "tan", "(", ")", "!", "OFF" },
			{ "PI", "y^x", "7", "8", "9", "/", "-" }, { "x^2", "sqrt(X)", "4", "5", "6", "*", "CA" },
			{ "e^x", "LN(X)", "1", "2", "3", "-", "C" }, { "LOG(X)", "1/X", "0", ".", "%", "+", "space" },
			{ "=", "Normal Exprission", "Postfix" } };
	static CursorStack<String> stack = new CursorStack<>();
	private static RadioButton deg = new RadioButton("Degree");
	private static RadioButton rad = new RadioButton("Radian");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		final TextField screen = CreateTextField();
		screen.setMinSize(50, 50);
		final TextField postFixScene = CreateTextField();
		postFixScene.setMinSize(50, 50);
		final TextField answer = CreateTextField();
		answer.setDisable(false);
		answer.setText("0");
		VBox screens = new VBox(10);
		ToggleGroup group = new ToggleGroup();
		deg.setToggleGroup(group);
		rad.setToggleGroup(group);
		group.selectToggle(deg);
		HBox radios = new HBox(10);
		radios.getChildren().addAll(deg, rad);
		screens.setPadding(new Insets(10, 10.5, 30, 12));
		screens.getChildren().addAll(screen, postFixScene, answer, radios);
		// gets buttons created
		final TilePane buttons = CreateButtons(screen, postFixScene, answer);
		primaryStage.setTitle("Calculator");
		primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.setResizable(false);
		// creates and sets the final layout of fx
		primaryStage.setScene(new Scene(CreateLayout(screens, buttons), 1250, 550));
		primaryStage.show();
	}

	private TextField CreateTextField() {
		final TextField screen = new TextField();
		screen.setStyle("-fx-background-color: white;");
		screen.setAlignment(Pos.CENTER_LEFT);
		screen.setFont(font3);
		screen.setEditable(false);
		screen.setDisable(true);
		return screen;
	}

	private TilePane CreateButtons(TextField screen1, TextField screen2, TextField screen3) {
		TilePane buttons = new TilePane();
		buttons.setVgap(7);
		buttons.setHgap(7);
		// Sets the maximum number of columns in this horizontal tilePane
		buttons.setPrefColumns(template[0].length - 1);
		for (String[] r : template) {
			for (String s : r) {
				buttons.getChildren().add(DecideButtonsAction(s, screen1, screen2, screen3));
			}
		}
		return buttons;
	}

	private Button DecideButtonsAction(String s, TextField screen1, TextField screen2, TextField screen3) {
		Button baseButton = new Button(s);
		baseButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		// s.mathches checks if a string mathces a given regular exprission
		// Where here it's a String of numbers from 0-9
		if (s.matches("[0-9]")) {
			NumericButton(baseButton, screen1, screen2, screen3);
		} else {
			// creates all binary operations listed : +-*/
			if (s.matches("[+|\\-|/|*]")) {
				OperandsOperations(baseButton, screen1, screen2, screen3);
			} else {
				switch (s) {
				case "OFF": {
					OffButton(baseButton, screen1, screen2, screen3);
					break;
				}
				case "C": {
					DeleteCharacter(baseButton, screen1, screen2);
					break;
				}
				case "CA": {
					ClearScreens(baseButton, screen1, screen2, screen3);
					break;
				}
				case "Postfix": {
					TurnPostfix(baseButton, screen1, screen2);
					break;
				}
				case "Normal Exprission": {
					TurnNormalExprission(baseButton, screen1, screen2);
					break;
				}
				case "sin": {
					OperandsOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case "cos": {
					OperandsOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case "tan": {
					OperandsOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case "(": {
					OperandsOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case ")": {
					OperandsOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case "!": {
					OperandsOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case "PI": {
					piButton(baseButton, screen1, screen2, screen3);
					break;
				}
				case "y^x": {
					PowerOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case "x^2": {
					PowerOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case "sqrt(X)": {
					UniaryOperands(baseButton, screen1, screen2, screen3);
					break;
				}
				case "e^x": {
					PowerOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case "LN(X)": {
					UniaryOperands(baseButton, screen1, screen2, screen3);
					break;
				}
				case "LOG(X)": {
					UniaryOperands(baseButton, screen1, screen2, screen3);
					break;
				}
				case "1/X": {
					PowerOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case ".": {
					NumericButton(baseButton, screen1, screen2, screen3);
					break;
				}
				case "%": {
					OperandsOperations(baseButton, screen1, screen2, screen3);
					break;
				}
				case "=": {
					EqualsOperation(baseButton, screen1, screen2, screen3);
					break;
				}
				case "space": {
					spaceButton(baseButton, screen1, screen2);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + s);
				}
			}
		}

		return baseButton;
	}

	// checks operation and evaluates the value
	private double makeUnaryEvaluation(String op, double a) {
		double result = 0;
		switch (op) {
		case "!":
			result = factorial((int) a);
			break;
		case "sqrt": {
			result = Math.sqrt(a);
			break;
		}
		case "e^": {
			result = Math.pow(Math.E, a);
			break;
		}
		case "LN": {
			result = Math.log(a);
			break;
		}
		case "LOG": {
			result = Math.log10(a);
			break;
		}
		case "1/": {
			result = 1 / a;
			break;
		}
		case "^2": {
			result = Math.pow(a, 2);
			break;
		}
		case "sin":
			result = Math.sin(a);
			break;

		case "cos": {
			result = Math.cos(a);
			break;
		}
		case "tan": {
			result = Math.tan(a);
			break;
		}
		}
		return result;
	}

	// checks operations and evaluates final value
	private String makeNormalExpression(String operation, double a, double b) {
		double result = 0;
		switch (operation) {
		case "+":
			result = a + b;
			break;
		case "-":
			result = b - a;
			break;
		case "/":
			result = b / a;
			break;
		case "*":
			result = a * b;
			break;
		case "^":
			result = Math.pow(b, a);
			break;
		case "%":
			result = b % a;
			break;
		case "sin":
			if (deg.isSelected()) {
				result = makeUnaryEvaluation(operation, Math.toRadians(a));
			} else {
				result = makeUnaryEvaluation(operation, (a));
			}
			break;

		case "cos": {
			if (deg.isSelected()) {
				result = makeUnaryEvaluation(operation, Math.toRadians(a));
			} else {
				result = makeUnaryEvaluation(operation, (a));
			}
			break;
		}
		case "tan": {
			if (deg.isSelected()) {
				result = makeUnaryEvaluation(operation, Math.toRadians(a));
			} else {
				result = makeUnaryEvaluation(operation, (a));
			}
			break;
		}
		default:
			result = makeUnaryEvaluation(operation, a);
		}
		return String.valueOf(result);
	}

	// when clicking equals button checks entered values and evaluates result if
	// entered values are correctly ordered
	private void EqualsOperation(Button btn, TextField screen1, TextField screen2, TextField screen3) {
		btn.setOnAction(e -> {
			// screen 1 takes normal expressions and evaluates them
			if (screen1.isEditable()) {
				if (!screen1.getText().isBlank()) {
					String expression = screen1.getText().strip();
					expression = expression.replaceAll("  ", " ");
					boolean correctParanthesis = checkParanthesis(expression);
					CursorStack<String> op = new CursorStack<>();
					if (correctParanthesis) {
						try {
							String[] exp1 = expression.split(" ");
							for (int i = 0; i < exp1.length; i++) {
								try {
									exp1[i] = exp1[i].trim();
									if ((!exp1[i].equals("1/")) && Character.isDigit(exp1[i].charAt(0))) {
										stack.push(exp1[i]);
									} else if (exp1[i].equals("+") || exp1[i].equals("-") || exp1[i].equals("/")
											|| exp1[i].equals("*") || exp1[i].equals("^") || exp1[i].equals("%")
											|| exp1[i].equals("sqrt") || exp1[i].equals("e^") || exp1[i].equals("LN")
											|| exp1[i].equals("LOG") || exp1[i].equals("1/") || exp1[i].equals("^2")
											|| exp1[i].equals("sin") || exp1[i].equals("cos") || exp1[i].equals("tan")
											|| exp1[i].equals("!")) {
										while (!op.isEmpty() && hasPrecedence(exp1[i], op.peek())) {
											if (op.peek().equals("+") || op.peek().equals("-") || op.peek().equals("/")
													|| op.peek().equals("*") || op.peek().equals("^")
													|| op.peek().equals("%")) {
												String s = (makeNormalExpression((String) op.pop(),
														Double.parseDouble((String) stack.pop()),
														Double.parseDouble((String) stack.pop())));
												stack.push(s);
											} else {
												String s1 = (makeNormalExpression((String) op.pop(),
														Double.parseDouble((String) stack.pop()), 0));
												stack.push(s1);
											}
										}

										// Push current token to 'ops'.
										op.push(exp1[i]);
									} else {
										switch (exp1[i]) {
//										
										case "(": {
											op.push(exp1[i]);
											break;
										}
										case ")": {
											while (!op.peek().equals("(")) {
												if (op.peek().equals("+") || op.peek().equals("-")
														|| op.peek().equals("/") || op.peek().equals("*")
														|| op.peek().equals("^") || op.peek().equals("%")) {
													String s = (makeNormalExpression((String) op.pop(),
															Double.parseDouble((String) stack.pop()),
															Double.parseDouble((String) stack.pop())));
													stack.push(s);
												} else {
													String s1 = (makeNormalExpression((String) op.pop(),
															Double.parseDouble((String) stack.pop()), 0));
													stack.push(s1);
												}
											}
											// pops the '(' char
											op.pop();
											break;
										}

										default:
											warning_Message("Unexpeted value");
										}

									}
								} catch (Exception ex) {
									System.out.println(stack.peek());
									System.out.println(op.peek());
									stack.clear();
									op.clear();
									warning_Message("Check your order and paranthesis");
								}

							}
							while (!op.isEmpty()) {
								if (op.peek().equals("+") || op.peek().equals("-") || op.peek().equals("/")
										|| op.peek().equals("*") || op.peek().equals("^") || op.peek().equals("%")) {
									String s = (makeNormalExpression((String) op.pop(),
											Double.parseDouble((String) stack.pop()),
											Double.parseDouble((String) stack.pop())));
//									stack.pop();
//									stack.pop();
//									op.pop();
									stack.push(s);
								} else {
									String s1 = (makeNormalExpression((String) op.pop(),
											Double.parseDouble((String) stack.pop()), 0));
									stack.push(s1);
								}
							}
						} catch (Exception ex) {
							stack.clear();
							op.clear();
							warning_Message("Error Unable to evalute");
						}
					}
					screen3.setText((String) stack.pop());
					stack.clear();
				}
			} else if (screen2.isEditable()) {// screen 2 evaluates postfix expressions
				// postfix takes paranthesis only for taking a double digit value or more
				String res = null;
				String expression = screen2.getText().strip();
				expression = expression.replaceAll("  ", " ");
				boolean correctParanthesis = checkParanthesis(expression);
				if (correctParanthesis) {
					String[] exp1 = expression.split(" ");
					for (int i = 0; i < exp1.length; i++) {
						exp1[i] = exp1[i].trim();
						boolean flag = true;
						double op1 = 0, op2 = 0, result = 0;
						switch (exp1[i]) {
						case "+":
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
										stack.pop();
										op2 = Double.parseDouble((String) stack.pop());
										stack.pop();
										result = op1 + op2;
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check Order 1");
										return;
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						case "-":
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
										stack.pop();
										op2 = Double.parseDouble((String) stack.pop());
										stack.pop();
										result = op2 - op1;
										System.out.println(result);
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("check order 2");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							} catch (Exception ex) {
								warning_Message("Check order 3");
							}
							break;
						case "/":
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
										stack.pop();
										op2 = Double.parseDouble((String) stack.pop());
										stack.pop();
										result = op2 / op1;
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 4");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						case "*":
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
										stack.pop();
										op2 = Double.parseDouble((String) stack.pop());
										stack.pop();
										result = op1 * op2;
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 5");
									}
								}
								break;
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
						case "sin": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
//										stack.pop();
										if (deg.isSelected()) {
											result = Math.sin(Math.toRadians(op1));
										} else {
											result = Math.sin(op1);
										}
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 6");
									}
								}
								break;
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
						}
						case "cos": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
//										stack.pop();
										if (deg.isSelected()) {
											result = Math.cos(Math.toRadians(op1));
										} else {
											result = Math.cos(op1);
										}
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 7");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						}
						case "tan": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
										stack.pop();
										if (deg.isSelected()) {
											result = Math.tan(Math.toRadians(op1));
										} else {
											result = Math.tan(op1);
										}
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("check order 8");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						}
						case "(": {
							warning_Message("Paranthesis are not defined in postfix");
							stack.clear();
							break;
						}
						case ")": {
							warning_Message("Paranthesis are not defined in postfix");
							stack.clear();
							break;
						}
						case "!": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
										result = factorial((int) op1);
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 9");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission 222");
							}
							break;
						}
						case "PI": {
							stack.push(String.valueOf(Math.PI));
							break;
						}
						case "^": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
										stack.pop();
										op2 = Double.parseDouble((String) stack.pop());
										stack.pop();
										result = Math.pow(op2, op1);
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check your order 10");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						}
						case "^2": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
//										stack.pop();
										result = Math.pow(op1, 2);
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 11");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						}
						case "sqrt": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
										result = Math.sqrt(op1);
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 12");
										return;
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						}
						case "e^": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
//										stack.pop();
										result = Math.pow(Math.E, op1);
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 13");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						}
						case "LN": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
//										stack.pop();
										result = Math.log(op1);
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 14");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						}
						case "LOG": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
//										stack.pop();
										result = Math.log10(op1);
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 15");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						}
						case "1/": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
//										stack.pop();
										result = 1 / op1;
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 16");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						}
						case "%": {
							try {
								if (!stack.isEmpty()) {
									try {
										op1 = Double.parseDouble((String) stack.pop());
										stack.pop();
										op2 = Double.parseDouble((String) stack.pop());
										stack.pop();
//										System.out.println(op1);
//										System.out.println(op2);
										result = op2 % op1;
//										System.out.println(result);
										stack.push(String.valueOf(result));
										flag = false;
									} catch (Exception ex) {
										warning_Message("Check order 17");
									}
								}
							} catch (NullPointerException ex) {
								warning_Message("Incorrect exprission");
							}
							break;
						}
						default:
							stack.push(exp1[i]);
						}
						if (flag)
							stack.push(exp1[i]);
						// i = exprission.indexOf(" ") + 1;
						res = String.valueOf(result);
					}
//				System.out.println("Result = " + stack.pop());
					if (!stack.peek().equals(res)) {
						warning_Message("Check your operatoin and numbers order");
						stack.clear();
						return;
					}
					screen3.setText((String) stack.pop());
//					if (!stack.isEmpty()) {
////						screen3.setText("0");
//						warning_Message("Check order extra or empty numbers or spaces");
//					}
					stack.clear();
				}
			}
		});
	}

	// checkes precedence of operations
	// returns true if first operation precedence is less than op2
	private static boolean hasPrecedence(String op1, String op2) {
		if (op2.equals("(") || op2.equals(")"))
			return false;
		if ((op1.equals("*") || op1.equals("/")) || op1.equals("1/") && (op2.equals("+") || op2.equals("-")))
			return false;
		if ((op1.equals("^") || op1.equals("^2") || op1.equals("!") || op1.equals("LOG") || op1.equals("sqrt")
				|| op1.equals("sin") || op1.equals("cos") || op1.equals("tan") || op1.equals("LN"))
				&& (op2.equals("*") || op2.equals("1/") || op2.equals("/") || op2.equals("+") || op2.equals("-"))) {
			return false;
		} else
			return true;
	}

	private VBox CreateLayout(VBox screen, TilePane buttons) {
		final VBox layout = new VBox(20);
		layout.setAlignment(Pos.CENTER);
		layout.setStyle("-fx-background-color: silver; -fx-padding: 10; -fx-font-size: 18;");
		layout.getChildren().setAll(screen, buttons);
		screen.prefWidthProperty().bind(buttons.widthProperty());
		return layout;
	}

	private void NumericButton(Button btn, TextField screen1, TextField screen2, TextField screen3) {
		btn.setOnAction(e -> {
			if (screen1.isEditable()) {
				screen1.appendText(btn.getText());
			} else if (screen2.isEditable()) {
				screen2.appendText(btn.getText());
			}
		});
	}

	private void piButton(Button btn, TextField screen1, TextField screen2, TextField screen3) {
		btn.setOnAction(e -> {
			if (screen1.isEditable()) {
				screen1.appendText("3.1415");
			} else if (screen2.isEditable()) {
				screen2.appendText(" 3.1415");
			}
		});
	}

	private void OperandsOperations(Button btn, TextField screen1, TextField screen2, TextField screen3) {
		btn.setOnAction(e -> {
			if (screen1.isEditable()) {
				screen1.appendText(" " + btn.getText() + " ");
			} else if (screen2.isEditable()) {
				screen2.appendText(" " + btn.getText() + " ");
			}
		});
	}

	private void UniaryOperands(Button btn, TextField screen1, TextField screen2, TextField screen3) {
		btn.setOnAction(e -> {
			if (screen1.isEditable()) {
				screen1.appendText(btn.getText().substring(0, btn.getText().indexOf("(")) + " ( ");
			} else if (screen2.isEditable()) {
				screen2.appendText(" " + btn.getText().substring(0, btn.getText().indexOf("(")) + " ");
			}
		});
	}

	private void PowerOperations(Button btn, TextField screen1, TextField screen2, TextField screen3) {
		btn.setOnAction(e -> {
			if (screen1.isEditable()) {
				if (btn.getText().equals("x^2"))
					screen1.appendText(" " + btn.getText().substring(1) + " ");
				else if (btn.getText().equals("e^x")) {
					screen1.appendText(" e^ ");
				} else if (btn.getText().equals("1/X")) {
					screen1.appendText(" 1/ ");
				} else {
					screen1.appendText(" ^ ");
				}
			} else if (screen2.isEditable()) {
				if (btn.getText().equals("x^2"))
					screen2.appendText(" " + btn.getText().substring(1) + " ");
				else if (btn.getText().equals("e^x")) {
					screen2.appendText(" e^ ");
				} else if (btn.getText().equals("1/X")) {
					screen2.appendText(" 1/ ");
				} else {
					screen2.appendText(" ^ ");
				}
			}
		});
	}

	private void OffButton(Button btn, TextField screen1, TextField screen2, TextField screen3) {
		btn.setOnAction(e -> {
			System.exit(0);
		});
	}

	private void DeleteCharacter(Button btn, TextField screen1, TextField scree2) {
		btn.setOnAction(e -> {
			if (screen1.isEditable()) {
				if (!screen1.getText().isBlank())
					screen1.setText(screen1.getText().substring(0, screen1.getText().length() - 1));
			} else if (scree2.isEditable()) {
				if (!scree2.getText().isBlank())
					scree2.setText(scree2.getText().substring(0, scree2.getText().length() - 1));
			}
		});
	}

	private void spaceButton(Button btn, TextField screen1, TextField screen2) {
		btn.setOnAction(e -> {
			if (screen1.isEditable()) {
				if (!screen1.getText().isBlank())
					screen1.appendText(" ");
			} else if (screen2.isEditable()) {
				if (!screen2.getText().isBlank())
					screen2.appendText(" ");
			}
		});
	}

	private void ClearScreens(Button btn, TextField screen1, TextField screen2, TextField screen3) {
		btn.setOnAction(e -> {
			screen1.clear();
			screen2.clear();
			screen3.clear();
		});
	}

	private void TurnNormalExprission(Button btn, TextField screen1, TextField screen2) {
		btn.setOnAction(e -> {
			screen1.clear();
			screen2.clear();
			screen1.setDisable(false);
			screen1.setEditable(true);
			screen2.setDisable(true);
			screen2.setEditable(false);
		});
	}

	private void TurnPostfix(Button btn, TextField screen1, TextField screen2) {
		btn.setOnAction(e -> {
			warning_Message("Note: place spaces between different numbers and operators");
			screen1.clear();
			screen2.clear();
			screen2.setDisable(false);
			screen2.setEditable(true);
			screen1.setDisable(true);
			screen1.setEditable(false);
		});
	}

	// checks paranthesis of an expression (if every opening has a closing )
	private boolean checkParanthesis(String str) {
		CursorStack<Character> stack = new CursorStack<>();
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '{' || str.charAt(i) == '[' || str.charAt(i) == '(') {
				stack.push(str.charAt(i));
			} else if (!(stack.isEmpty()) && (str.charAt(i) == ')' && (char) stack.peek() == '(')) {
				stack.pop();
			} else if (!(stack.isEmpty()) && (str.charAt(i) == ')')) {
				stack.push(str.charAt(i));
			}
		}
		if (!stack.isEmpty())
			warning_Message("Check Paranthesis");

		return stack.isEmpty();
	}

	public static void warning_Message(String x) {
		Alert alert = new Alert(AlertType.NONE);
		alert.setAlertType(AlertType.WARNING);
		alert.setContentText(x);
		alert.show();
	}

	private int factorial(int x) {
		int result = 1;
		if (x == 0)
			return 1;
		for (int i = x; i > 0; i--) {
			result *= i;
		}
		return result;
	}

	public static void confirmation_Message(String x) {
		Alert alert = new Alert(AlertType.NONE);
		alert.setAlertType(AlertType.CONFIRMATION);
		alert.setContentText(x);
		alert.show();
	}

}
